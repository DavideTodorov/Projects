class Player:
    def __init__(self, name, cards_deck):
        self.name = name
        self.cards_deck = cards_deck

    def remove_one(self):
        return self.cards_deck.pop(0)

    def add_cards(self, cards_to_add):
        if isinstance(cards_to_add, list):
            self.cards_deck.extend(cards_to_add)
        else:
            self.cards_deck.append(cards_to_add)

    def __str__(self):
        return f'Player {self.name} has {len(self.cards_deck)} cards.'
