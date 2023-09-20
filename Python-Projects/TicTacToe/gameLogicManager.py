def is_there_win_condition(board):
    result = False
    result = is_there_win_condition_row1(board)
    result = is_there_win_condition_row2(board)
    result = is_there_win_condition_row3(board)
    result = is_there_win_condition_col1(board)
    result = is_there_win_condition_col2(board)
    result = is_there_win_condition_col3(board)
    result = is_there_win_condition_diagonal1(board)
    result = is_there_win_condition_diagonal2(board)
    return result


def is_there_win_condition_row1(board):
    if board[0] == board[1] == board[2] and board[0] in ['X', 'O']:
        return True
    else:
        return False


def is_there_win_condition_row2(board):
    if board[3] == board[4] == board[5] and board[3] in ['X', 'O']:
        return True
    else:
        return False


def is_there_win_condition_row3(board):
    if board[6] == board[7] == board[8] and board[6] in ['X', 'O']:
        return True
    else:
        return False


def is_there_win_condition_col1(board):
    if board[0] == board[3] == board[6] and board[0] in ['X', 'O']:
        return True
    else:
        return False


def is_there_win_condition_col2(board):
    if board[1] == board[4] == board[7] and board[1] in ['X', 'O']:
        return True
    else:
        return False


def is_there_win_condition_col3(board):
    if board[2] == board[5] == board[8] and board[2] in ['X', 'O']:
        return True
    else:
        return False


def is_there_win_condition_diagonal1(board):
    if board[0] == board[4] == board[8] and board[0] in ['X', 'O']:
        return True
    else:
        return False


def is_there_win_condition_diagonal2(board):
    if board[2] == board[4] == board[6] and board[2] in ['X', 'O']:
        return True
    else:
        return False
