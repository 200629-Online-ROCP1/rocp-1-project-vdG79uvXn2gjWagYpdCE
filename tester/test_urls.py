#!/usr/bin/python3

import requests
import simplejson

base_url = "http://localhost:8080/rocp-project/api/"


def get_token(username, password):
    payload = {"username": username, "password": password}
    res = requests.post(base_url + "auth", json=payload)
    if res.status_code != 200:
        print(
            f"FAILURE: received status code {res.status_code} for auth {res.url} endpoint {username}"
        )
        return False
    try:
        json = res.json()
        return json["token"]
    except simplejson.errors.JSONDecodeError as err:
        print(f"FAILURE: auth returned bad json => {err}")
        return False


def test_bad_token(username, password):
    payload = {"username": username, "password": password}
    res = requests.post(base_url + "auth", json=payload)
    if res.status_code != 401:
        print(
            f"FAILURE: received status code {res.status_code} invalid username and password"
        )


def check(method, endpoint, expected_status=200, token=None, json=None):
    headers = {}
    if token:
        headers = {"Authorization": "Bearer " + token}
    res = requests.request(method, base_url + endpoint, headers=headers, json=json)
    if res.status_code != expected_status:
        print(
            f"FAILURE: {method} {res.url} returned status code {res.status_code} not {expected_status}"
        )
    try:
        json = res.json()
    except simplejson.errors.JSONDecodeError as err:
        print(f"FAILURE: {method} {res.url} returned bad json => {err}")
    return res.content


if __name__ == "__main__":
    tokens = {
        "Standard": get_token("hsimpson", "password"),
        "NonOwner": get_token("wsmithers", "password"),
        "Employee": get_token("rwiggum", "password"),
        "Admin": get_token("msimpson", "password"),
    }
    test_bad_token("nflanders", "badentry")
    test_bad_token("unknown", "password")

    # 46 => hsimpson
    # 58 => hsimpson owns
    ID = {"users": "/46", "accounts": "/58"}

    check("GET", "accounts/status/44", expected_status=200, token=tokens["Admin"])
    check("GET", "accounts/status/44", expected_status=200, token=tokens["Employee"])
    check("GET", "accounts/status/44", expected_status=403, token=tokens["Standard"])

    check("GET", "accounts/owner/46", expected_status=200, token=tokens["Admin"])
    check("GET", "accounts/owner/46", expected_status=200, token=tokens["Employee"])
    check("GET", "accounts/owner/46", expected_status=200, token=tokens["Standard"])
    check("GET", "accounts/owner/46", expected_status=403, token=tokens["NonOwner"])

    for endpoint in ["users", "accounts"]:
        # list endpoints
        check("GET", endpoint, expected_status=401)
        check("GET", endpoint, expected_status=200, token=tokens["Admin"])
        check("GET", endpoint, expected_status=200, token=tokens["Employee"])
        check("GET", endpoint, expected_status=403, token=tokens["Standard"])
        # detail endpoints
        check("GET", endpoint + ID[endpoint], expected_status=401)
        check("GET", endpoint + ID[endpoint], expected_status=200, token=tokens["Admin"])
        check("GET", endpoint + ID[endpoint], expected_status=200, token=tokens["Employee"])
        check("GET", endpoint + ID[endpoint], expected_status=200, token=tokens["Standard"])
        check("GET", endpoint + ID[endpoint], expected_status=403, token=tokens["NonOwner"])

    user_put = {
        "firstname": "Homer",
        "role": "Standard",
        "deleted": "false",
        "accountholder_id": 46,
        "email": "doh2112@gmail.com",
        "username": "hsimpson",
        "password": "password",
        "lastname": "Simpson"
        }
    check("PUT", "users", expected_status=202, token=tokens["Admin"], json=user_put)
    check("PUT", "users", expected_status=403, token=tokens["Employee"], json=user_put)
    check("PUT", "users", expected_status=202, token=tokens["Standard"], json=user_put)
    check("PUT", "users", expected_status=403, token=tokens["NonOwner"], json=user_put)
    account_put = {
        "account_id": 58,
        "deleted": "false",
        "balance": "134.0",
        "accountstatus": "Open",
        "accounttype": "Checking",
        "accountholder": "hsimpson",
        }
    check("PUT", "accounts", expected_status=202, token=tokens["Admin"], json=account_put)
    check("PUT", "accounts", expected_status=403, token=tokens["Employee"], json=account_put)
    check("PUT", "accounts", expected_status=403, token=tokens["Standard"], json=account_put)
    check("PUT", "accounts", expected_status=403, token=tokens["NonOwner"], json=account_put)

    account_post = {
        "deleted": "false",
        "balance": "250.0",
        "accountstatus": "Open",
        "accounttype": "Savings",
        "accountholder": "hsimpson",
        }
    check("POST", "accounts", expected_status=201, token=tokens["Admin"], json=account_post)
    check("POST", "accounts", expected_status=201, token=tokens["Employee"], json=account_post)
    check("POST", "accounts", expected_status=201, token=tokens["Standard"], json=account_post)
    check("POST", "accounts", expected_status=403, token=tokens["NonOwner"], json=account_post)

    tmp = "BadInput"
    for field in ["accountstatus", "accounttype", "accountholder"]:
        tmp, account_post[field] = account_post[field], tmp
        check("POST", "accounts", expected_status=400, token=tokens["Admin"], json=account_post)
        account_post[field], tmp = tmp, account_post[field]
 
    import random
    extra = random.randint(0, 5000)
    user_post = {
        "deleted": "false",
        "firstname": "New",
        "role": "Standard",
        "email": f"new_user{extra}@gmail.com",
        "username": f"newuser{extra}",
        "password": "something",
        "lastname": "User"
        }
    check("POST", "register", expected_status=201, token=tokens["Admin"], json=user_post)
    check("POST", "register", expected_status=403, token=tokens["Employee"], json=user_post)
    check("POST", "register", expected_status=403, token=tokens["Standard"], json=user_post)

    check("DELETE", "users/58", expected_status=202, token=tokens["Admin"])
    check("DELETE", "users/58", expected_status=403, token=tokens["Employee"])
    check("DELETE", "users/58", expected_status=403, token=tokens["Standard"])

    check("DELETE", "accounts/99", expected_status=202, token=tokens["Admin"])
    check("DELETE", "accounts/99", expected_status=403, token=tokens["Employee"])
    check("DELETE", "accounts/99", expected_status=403, token=tokens["Standard"])
    