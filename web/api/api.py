'''
Created on Nov 19, 2013

@author: FirstName LastName
'''
from Crypto import Random
from Crypto.Hash import SHA256

import bottle
from bottle import request, get, abort, post
import beaker.middleware
import json
from authorization import authorization

users = {         
'roberto': { 'pwd': 'a1159e9df3670d549d04524532629f5477ceb7deec9b45e47e8c009506ecb2c8', }, #pwd
'guest': { 'pwd': '84983c60f7daadc1cb8698621f802c0d9f9a3c3c295c810748fb048115c186ec', }, #guest
}

session_opts = {
    'session.type': 'file',
    'session.cookie_expires': 300,
    'session.data_dir': '/tmp/beaker-data',
    'session.auto': True
}

bapp=bottle.app()
app = beaker.middleware.SessionMiddleware(bapp, session_opts)

@post('/login/<username>')
def login(username):
    s = request.environ['beaker.session']

    if username not in users:
        abort(401, "Unknown user")
    
    print request.json
    
    composed = users[username]['pwd'] +  request.json['nonce']
    
    h = SHA256.new()
    h.update(composed)
    
    if h.hexdigest() !=  request.json['hpwd']:
        abort(401, "Wrong password")
    
    if 'SSK' not in s:
        s['SSK'] = Random.get_random_bytes(32).encode('hex')

    s['username'] = username

    result = {'text': "ciao " + username, 'ssk': s['SSK'] }
    return json.dumps(result);

@get('/data')
def data():
    s = request.environ['beaker.session']        
    authorization(request, s);
    
    result = {'data': "Some data for the user " + s['username'] }
    return json.dumps(result);   
