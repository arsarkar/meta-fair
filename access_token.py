#Class to provide host of the portal and the API key (to be passed to other classes)
class AccessToken:
    def __init__(self, portal_host, pass_key):
        self.auth = 'apikey=' + pass_key
        self.url = portal_host
