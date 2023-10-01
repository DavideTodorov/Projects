class Player:
    def __init__(self, name):
        self.name = name
        self.cards_deck = []

    def remove_one(self):
        return self.cards_deck.pop(0)

    def add_cards(self, cards_to_add):
        if isinstance(cards_to_add, list):
            self.cards_deck.extend(cards_to_add)
        else:
            self.cards_deck.append(cards_to_add)

    def get_all_cards(self):
        return self.cards_deck

    def __str__(self):
        return f'Player {self.name} has {len(self.cards_deck)} cards.'
