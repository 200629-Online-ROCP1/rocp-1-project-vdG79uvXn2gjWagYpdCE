#!/usr/bin/python3

import requests
import simplejson

base_url = "http://localhost:8080/rocp-project/api/"

endpoints = ["user", "account", "accounttype", "accountstatus", "role"]

results = [] # [FAILURES, SUCCESSES]

def check(res, endpoint, descriptor, checkJSON=True):
    if res.status_code!=200:
        print(f"FAILURE: {descriptor} for {endpoint} returned status code {res.status_code}")
        return [1, 0]
    elif checkJSON:
        try:
            json = res.json()
            return [0, 1]
        except simplejson.errors.JSONDecodeError as err:
            print(f"FAILURE: {descriptor} for {endpoint} returned bad json => {err}")
            return [1, 0]
    return [0, 1]
 
def dsum(results):
    ret = [0, 0]
    for r in results:
        ret = [ret[0]+r[0], ret[1]+r[1]]
    return ret

for endpoint in endpoints:
    res = requests.get(base_url + endpoint + "/")
    results.append(check(res, endpoint, "List", checkJSON=True))
    res = requests.get(base_url + endpoint + "/1/")
    results.append(check(res, endpoint, "Detail", checkJSON=True))
    res = requests.get(base_url + endpoint + "/1000/")
    results.append(check(res, endpoint, "ID Unknown", checkJSON=False))
    
print(f"SUCCESS => {dsum(results)[1]}")
print(f"FAILURE => {dsum(results)[0]}")