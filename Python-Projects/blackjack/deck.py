import random
import config
import card


class Deck:
    def __init__(self):
        self.deck = []
        for suit in config.suits:
            for rank in config.ranks:
                self.deck.append(card.Card(suit, rank))

    def __str__(self):
        deck_string = ''
        for c in self.deck:
            deck_string += '\n' + c.__str__()
        return f'The deck has: {deck_string}'

    def shuffle(self):
        random.shuffle(self.deck)

    def deal(self):
        return self.deck.pop()
