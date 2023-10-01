import deck
import player


def add_cards_to_players(george, jessica, cards_deck):
    for x in range(26):
        george.add_cards(cards_deck.deal_one())
        jessica.add_cards(cards_deck.deal_one())


if __name__ == '__main__':
    player_one = player.Player('George')
    player_two = player.Player('Jessica')

    card_deck = deck.Deck()

    add_cards_to_players(player_one, player_two, card_deck)

    round_number = 0
    game_on = True
    cards_to_draw_on_war = 7

    while game_on:
        round_number += 1
        print(f'Round {round_number}')

        if len(player_one.get_all_cards()) == 0:
            print(f'{player_one.name} is out of cards. {player_two.name} wins!')
            game_on = False
            break

        if len(player_two.get_all_cards()) == 0:
            print(f'{player_two.name} is out of cards. {player_one.name} wins!')
            game_on = False
            break

        player_one_cards = [player_one.remove_one()]

        player_two_cards = [player_two.remove_one()]

        at_war = True
        while at_war:
            player_one_current_card = player_one_cards[-1]
            player_two_current_card = player_two_cards[-1]

            if player_one_current_card.value > player_two_current_card.value:
                player_one.add_cards(player_one_cards)
                player_one.add_cards(player_two_cards)
                at_war = False
                break
            elif player_one_current_card.value < player_two_current_card.value:
                player_two.add_cards(player_one_cards)
                player_two.add_cards(player_two_cards)
                at_war = False
                break

            print('WAR!')

            if len(player_one.get_all_cards()) < cards_to_draw_on_war:
                print(f'Player {player_one.name} unable to declare war! Player {player_two.name} wins!')
                game_on = False
                break
            elif len(player_two.get_all_cards()) < cards_to_draw_on_war:
                print(f'Player {player_two.name} unable to declare war! Player {player_one.name} wins!')
                game_on = False
                break
            else:
                for x in range(cards_to_draw_on_war):
                    player_one_cards.append(player_one.remove_one())
                    player_two_cards.append(player_two.remove_one())
