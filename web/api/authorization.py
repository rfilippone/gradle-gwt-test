'''
Created on Nov 22, 2013

@author: FirstName LastName
'''
from bottle import abort

def authorization(request, session):
    
    if session.last_accessed is None:
        abort(401, "Sorry, session expired.")

    if 'username' not in session:
        abort(401, "No username defined");

    if 'SSK' not in session:
        abort(401, "No SSK defined");
    
    for key in request.headers.keys():
        print(key + '=' + request.headers[key])

    authHeader = request.headers['X-HMAC-Auth']
    
    if authHeader is None:
        abort(401, "Missing authentication information")

    authElements = authHeader.split('/');
    if len(authElements) != 4:
        abort(401, "Wrong format of authentication information") 
        
    authMethod = authElements[0]    
    if authMethod != 'REST-HMAC-SHA256_1.0.0':
        abort(401, "Unsupported authentication scheme") 
        
    user = authElements[1]
    if user != session['username']:
        abort(401, "Wrong user")
    
    return