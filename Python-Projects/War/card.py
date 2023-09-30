import config


class Card:
    def __init__(self, rank, suit):
        self.suit = suit
        self.rank = rank
        self.value = config.values[rank]

    def __str__(self):
        return f'{self.rank} of {self.suit}'
