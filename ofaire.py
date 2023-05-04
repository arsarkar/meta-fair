import json
import requests
from fair import fair

class OFaire(fair):
    '''
    Pass the type of portal. permitted values: agroportal/industryportal/bioportal
    '''
    def __init__(self, portal, config) -> None:
        self.portal = portal
        self.config = config

    def check(self, uri)->bool:
        from requests.exceptions import HTTPError
        try:
            response = requests.get(self.config.url+"fair", params={'portal':self.portal, 'ontologies':uri, 'apikey':self.config.auth})
            if response.status_code == 200:
                self.result = response.json
            else:
                self.error = response.reason
        except HTTPError as e:
            self.error = e.response.reason

    def get_overall_score(self)->float:
        return self.result['ontologies']['IOF-CORE']['score'] / self.result['ontologies']['IOF-CORE']['maxCredits']