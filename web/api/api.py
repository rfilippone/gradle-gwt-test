'''
Created on Nov 19, 2013

@author: FirstName LastName
'''
from Crypto import Random

import bottle
from bottle import request, get
import beaker.middleware
import json
from authorization import authorization

users = {         
'roberto': { 'pwd': 'pwd', },
'guest': { 'pwd': 'guest', },
}

session_opts = {
    'session.type': 'file',
    'session.cookie_expires': 300,
    'session.data_dir': '/tmp/beaker-data',
    'session.auto': True
}

bapp=bottle.app()
app = beaker.middleware.SessionMiddleware(bapp, session_opts)

@get('/login/<username>')
def login(username):
    s = request.environ['beaker.session']
    s['username'] = username
    
    if 'SSK' not in s:
        s['SSK'] = Random.get_random_bytes(32).encode('hex')

    result = {'text': "ciao " + username, 'ssk': s['SSK'] }
    return json.dumps(result);

@get('/data')
def data():
    s = request.environ['beaker.session']        
    authorization(request, s);
    
    result = {'data': "Some data for the user " + s['username'] }
    return json.dumps(result);   
