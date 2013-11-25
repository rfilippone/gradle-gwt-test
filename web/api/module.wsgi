
'''
Created on Nov 19, 2013

@author: FirstName LastName
'''
from bottle import run

import sys, os
sys.path.append(os.path.dirname(__file__))

from api import app as application

if __name__ == '__main__':
    run(application, host='localhost', port=8080, debug=True)