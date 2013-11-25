'''
Created on Nov 19, 2013

@author: FirstName LastName
'''
from Crypto import Random

import bottle
from bottle import request, get, abort
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
    if s.last_accessed is None:        
        s['SSK'] = Random.get_random_bytes(32).encode('hex')
    
    s['username'] = username
    
    result = {'text': "ciao " + username, 'ssk': s['SSK'] }
    return json.dumps(result);

@get('/data')
def data():
    s = request.environ['beaker.session']
    if s.last_accessed is None:        
        abort(401, "Sorry, session expired.")
        
    authorization(request, s);
    
    result = {'data': "Some data for the user " + s['username'] }
    return json.dumps(result);   
