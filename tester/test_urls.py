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
        "firstname": "Nelson",
        "role": "Employee",
        "accountholder_id": 50,
        "email": "something_new@gmail.com",
        "username": "nmuntz",
        "password": "password",
        "lastname": "Muntz"
        }
    check("PUT", "users", expected_status=202, token=tokens["Admin"], json=user_put)
    check("PUT", "users", expected_status=403, token=tokens["Employee"], json=user_put)
    # check("PUT", "users", expected_status=202, token=tokens["Standard"], json=user_put)
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
