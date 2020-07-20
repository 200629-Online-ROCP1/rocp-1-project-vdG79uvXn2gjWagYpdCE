#!/usr/bin/python3

import requests
import simplejson

base_url = "http://localhost:8080/rocp-project/api/"

endpoints = ["users", "account"]

results = [] # [FAILURES, SUCCESSES]

def check(res, endpoint, descriptor, checkJSON=True, status_code=200):
    if res.status_code!=status_code:
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

# for endpoint in endpoints:
#     res = requests.get(base_url + endpoint + "/")
#     results.append(check(res, endpoint, "List", checkJSON=True))
#     res = requests.get(base_url + endpoint + "/1/")
#     results.append(check(res, endpoint, "Detail", checkJSON=True))
#     res = requests.get(base_url + endpoint + "/1000/")
#     results.append(check(res, endpoint, "ID Unknown", checkJSON=False, status_code=404))
#     res = requests.get(base_url + endpoint + "/abc/")
#     results.append(check(res, endpoint, "Bad ID Provided", checkJSON=False, status_code=400))
    

# res = requests.post(base_url + "accountstatus/", data = {"status": "FromPost", "another": "thing"})


# print(f"SUCCESS => {dsum(results)[1]}")
# print(f"FAILURE => {dsum(results)[0]}")

   
def get_token(username, password):
    payload = {'username': username, 'password': password}
    res = requests.post(base_url + 'auth', json=payload)
    if res.status_code != 200:
        print(f"FAILURE: received status code {res.status_code} for auth {res.url} endpoint")
        return False
    try:
        json = res.json()
        return json['token']
    except simplejson.errors.JSONDecodeError as err:
        print(f"FAILURE: auth returned bad json => {err}")
        return False

def test_bad_token(username, password):
    payload = {'username': username, 'password': password}
    res = requests.post(base_url + 'auth', json=payload)
    if res.status_code != 401:
        print(f"FAILURE: received status code {res.status_code} invalid username and password")

if __name__=='__main__':
    tokens = {}
    tokens['Standard'] = get_token("hsimpson", "password")
    tokens['Employee'] = get_token("rwiggum", "password")
    tokens['Admin'] = get_token("msimpson", "password")
    test_bad_token('nflanders', 'badentry')
    test_bad_token('unknown', 'password')