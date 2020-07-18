#!/usr/bin/python3

import requests
import simplejson

base_url = "http://localhost:8080/rocp-project/api/"

endpoints = ["user", "account", "accounttype", "accountstatus", "role"]

fail = 0
success = 0

for endpoint in endpoints:
    list = requests.get(base_url + endpoint + "/")
    if list.status_code!=200:
        print(f"FAILURE: list for {endpoint} returned status code {list.status_code}")
        fail += 1
    else:
        try:
            print(f"{list.json()}")
            success += 1
        except simplejson.errors.JSONDecodeError as err:
            fail += 1
            print(f"FAILURE: list for {endpoint} returned bad json => {err}")

    detail = requests.get(base_url + endpoint + "/1/")
    if detail.status_code!=200:
        print(f"FAILURE: detail for {endpoint} returned status code {detail.status_code}")
        fail += 1
    else:
        try:
            json = detail.json()
            success += 1
            print(f"SUCCESS: detail for {endpoint} returns valid json")
        except simplejson.errors.JSONDecodeError as err:
            fail += 1
            print(f"FAILURE: detail for {endpoint} returned bad json => {err}")

print(f"SUCCESS => {success}")
print(f"FAILURE => {fail}")