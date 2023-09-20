from IPython.display import clear_output
import boardManager

board_values_list = [' '] * 9

user1_symbol = ''
user2_symbol = ''


def get_user1_symbol_preference():
    global user1_symbol
    global user2_symbol
    user1_choice = ''
    while user1_choice not in ['X', 'O']:
        user1_choice = input('Please choose X or O: ')

    if user1_choice == 'X':
        user1_symbol = 'X'
        user2_symbol = 'O'
    else:
        user1_symbol = 'O'
        user2_symbol = 'X'


get_user1_symbol_preference()
print(f'User 1 is playing with {user1_symbol}')
print(f'User 2 is playing with {user2_symbol}')
