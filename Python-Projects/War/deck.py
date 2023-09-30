import config
import card
import random


def get_card_deck():
    cards = []
    for suit in config.suits:
        for rank in config.ranks:
            card_created = card.Card(rank, suit)
            cards.append(card_created)

    random.shuffle(cards)
    return cards


class Deck:
    def __init__(self):
        self.cards = get_card_deck()

    def get_cards(self):
        return self.cards

    def deal_one(self):
        return self.cards.pop()
