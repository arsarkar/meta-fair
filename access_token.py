#Class to provide host of the portal and the API key (to be passed to other classes)
class AccessToken:
    def __init__(self, config):
        with open(config) as f:
            self.url = f.readline().strip()
            self.auth = f.readline().strip()
