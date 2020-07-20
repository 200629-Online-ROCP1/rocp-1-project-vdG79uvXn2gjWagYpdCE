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
    return res.json()

def deposit(account, amount, token):
    content = check("GET", "accounts/" + account, expected_status=200, token=token)
    balance = float(content['balance'])
    post = {"accountId": account, "amount": amount}
    content = check("POST", "deposit", expected_status=200, token=token, json=post)
    print(content['message'])
    content = check("GET", "accounts/" + account, expected_status=200, token=token)
    new_balance = float(content['balance'])
    if new_balance != amount + balance:
        print(f"FAILURE: the new balance {new_balance} does not match {amount + balance}")

def withdraw(account, amount, token):
    content = check("GET", "accounts/" + account, expected_status=200, token=token)
    balance = float(content['balance'])
    post = {"accountId": account, "amount": amount}
    content = check("POST", "withdraw", expected_status=200, token=token, json=post)
    print(content['message'])
    content = check("GET", "accounts/" + account, expected_status=200, token=token)
    new_balance = float(content['balance'])
    if new_balance != balance - amount:
        print(f"FAILURE: the new balance {new_balance} does not match {balance - amount}")

def transfer(source, destination, amount, token):
    content = check("GET", "accounts/" + source, expected_status=200, token=token)
    source_balance = float(content['balance'])
    content = check("GET", "accounts/" + destination, expected_status=200, token=token)
    destination_balance = float(content['balance'])

    post = {"sourceAccountId": source, "amount": amount, "targetAccountId": destination}
    content = check("POST", "transfer", expected_status=200, token=token, json=post)
    print(content['message'])

    content = check("GET", "accounts/" + source, expected_status=200, token=token)
    new_source_balance = float(content['balance'])
    content = check("GET", "accounts/" + destination, expected_status=200, token=token)
    new_destination_balance = float(content['balance'])
    if new_source_balance != source_balance - amount:
        print(f"FAILURE: the new source balance {new_source_balance} does not match {source_balance - amount}")
    if new_destination_balance != destination_balance + amount:
        print(f"FAILURE: the new source balance {new_destination_balance} does not match {destination_balance + amount}")

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

    deposit("58", 12.50, tokens["Admin"])
    deposit("58", 360, tokens["Admin"])
    deposit("58", 12.34, tokens["Admin"])

    post = {"accountId": 58, "amount": 10.25}
    content = check("POST", "deposit", expected_status=200, token=tokens["Admin"], json=post)
    content = check("POST", "deposit", expected_status=403, token=tokens["Employee"], json=post)
    content = check("POST", "deposit", expected_status=200, token=tokens["Standard"], json=post)
    content = check("POST", "deposit", expected_status=403, token=tokens["NonOwner"], json=post)
    
    withdraw("58", 12.50, tokens["Admin"])
    withdraw("58", 360, tokens["Admin"])
    withdraw("58", 12.34, tokens["Admin"])

    post = {"accountId": 58, "amount": 10.25}
    content = check("POST", "withdraw", expected_status=200, token=tokens["Admin"], json=post)
    content = check("POST", "withdraw", expected_status=403, token=tokens["Employee"], json=post)
    content = check("POST", "withdraw", expected_status=200, token=tokens["Standard"], json=post)
    content = check("POST", "withdraw", expected_status=403, token=tokens["NonOwner"], json=post)

    transfer("58", "61", 100, tokens['Admin'])