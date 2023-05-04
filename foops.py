import json
import requests
from fair import fair

class Foops(fair):
    def __init__(self) -> None:
        self.headers = {
            'accept': 'application/json;charset=UTF-8',
            'Content-Type': 'application/json;charset=UTF-8',
        }
        self.api = 'http://localhost:1099/assessOntology'

    def check(self, uri)->bool:
        from requests.exceptions import HTTPError
        try:
            response = requests.post(self.api, headers=self.headers, json={'ontologyUri': uri})
            if response.status_code == 200:
                self.result = response.json
                self.error = None
            else:
                self.error = response.reason
        except HTTPError as e:
            self.error = e.response.reason

    def get_overall_score(self)->float:
        return self.result['overall_score']
