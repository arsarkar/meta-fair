import json
import requests
from fair import fair

class Fcheker(fair):
    def __init__(self) -> None:
        self.api = 'https://fair-checker.france-bioinformatique.fr/api/check/metrics_all'

    def check(self, uri)->bool:
        from requests.exceptions import HTTPError
        try:
            response = requests.get(self.api, params={'url':uri})
            if response.status_code == 200:
                self.result = response.json
                self.error = None
            else:
                self.error = response.reason
        except HTTPError as e:
            self.error = e.response.reason

    def get_overall_score(self)->float:
        s = 0
        for x in self.result:
            s = s + int(x['score'])
        return float(s)