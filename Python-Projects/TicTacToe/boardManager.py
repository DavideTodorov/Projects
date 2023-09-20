from IPython.display import clear_output


def print_board(board):
    clear_output()
    print('            1   2   3')
    print(f'row1      |{board[0]}|{board[1]}|{board[2]}|')
    print(f'row2      |{board[3]}|{board[4]}|{board[5]}|')
    print(f'row3      |{board[6]}|{board[7]}|{board[8]}|')


def update_board(board, index, symbol):
    board[index] = f' {symbol} '
    return board
