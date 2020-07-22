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

def get_users(token=None):
    users = {}
    headers = {"Authorization": "Bearer " + token} if token else {}
    res = requests.request("GET", base_url + "users", headers=headers)
    try:
        json = res.json()
    except simplejson.errors.JSONDecodeError as err:
        print(f"FAILURE: get_users returned bad json => {err}")
        return False
    for user in json:
        users[user['username']] = user
    return users

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
    return json

