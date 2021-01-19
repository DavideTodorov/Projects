"use strict";

//Accounts data
const account1 = {
  owner: "Jonas Schmedtmann",
  movements: [200, 450, -400, 3000, -650, -130, 70, 1300],
  interestRate: 1.2,
  pin: 1111,
  movementsDates: [
    { id: 200, date: "2019-11-18T21:31:17.178" },
    { id: 450, date: "2019-12-23T07:42:02.383" },
    { id: -400, date: "2020-01-28T09:15:04.904" },
    { id: 3000, date: "2020-04-01T10:17:24.185" },
    { id: -650, date: "2020-05-08T14:11:59.604" },
    { id: -130, date: "2020-05-27T17:01:17.194" },
    { id: 70, date: "2021-01-10T23:36:17.929" },
    { id: 1300, date: "2021-01-17T10:51:36.790" },
  ],
  currency: "EUR",
  locale: "pt-PT",
};

const account2 = {
  owner: "Jessica Davis",
  movements: [5000, 3400, -150, -790, -3210, -1000, 8500, -30],
  interestRate: 1.5,
  pin: 2222,
  movementsDates: [
    { id: 5000, date: "2019-11-01T13:15:33.035" },
    { id: 3400, date: "2019-11-30T09:48:16.867" },
    { id: -150, date: "2019-12-25T06:04:23.907" },
    { id: -790, date: "2020-01-25T14:18:46.235" },
    { id: -3210, date: "2020-02-05T16:33:06.386" },
    { id: -1000, date: "2021-01-11T14:43:26.374" },
    { id: 8500, date: "2021-01-13T18:49:59.371" },
    { id: -30, date: "2021-01-16T12:01:20.894" },
  ],
  currency: "USD",
  locale: "en-US",
};

const accounts = [account1, account2];

//
//Elements
const labelWelcome = document.querySelector(".welcome");
const labelDate = document.querySelector(".date");
const labelBalance = document.querySelector(".balance__value");
const labelSumIn = document.querySelector(".summary__value--in");
const labelSumOut = document.querySelector(".summary__value--out");
const labelSumInterest = document.querySelector(".summary__value--interest");
const labelTimer = document.querySelector(".timer");

const containerApp = document.querySelector(".app");
const containerMovements = document.querySelector(".movements");

const btnLogin = document.querySelector(".login__btn");
const btnTransfer = document.querySelector(".form__btn--transfer");
const btnLoan = document.querySelector(".form__btn--loan");
const btnClose = document.querySelector(".form__btn--close");
const btnSort = document.querySelector(".btn--sort");

const inputLoginUsername = document.querySelector(".login__input--user");
const inputLoginPin = document.querySelector(".login__input--pin");
const inputTransferTo = document.querySelector(".form__input--to");
const inputTransferAmount = document.querySelector(".form__input--amount");
const inputLoanAmount = document.querySelector(".form__input--loan-amount");
const inputCloseUsername = document.querySelector(".form__input--user");
const inputClosePin = document.querySelector(".form__input--pin");

const locale = navigator.language;

//==============================================
//Calc how mnany days have passed since movement
const calcDaysPassed = function (paramDate) {
  const date = new Date(paramDate);
  const currDate = new Date();
  return Math.round((currDate - date) / (1000 * 60 * 60 * 24));
};

//Format and return date method
const formatCurrDate = function (paramDate) {
  const date = new Date(paramDate);
  const daysPassed = calcDaysPassed(date);

  if (daysPassed === 0) {
    return "Today";
  } else if (daysPassed === 1) {
    return "Yesterday";
  } else if (daysPassed > 1 && daysPassed <= 7) {
    return `${daysPassed} days ago`;
  }

  const options = {
    day: "numeric",
    month: "long",
    year: "numeric",
  };

  const formattedDate = new Intl.DateTimeFormat(locale, options).format(date);

  return formattedDate;
};
//============================================
//Method to format currencies to locale style
const formatCurrency = function (amount) {
  return `${new Intl.NumberFormat(locale, {
    style: "currency",
    currency: "EUR",
  }).format(amount)}`;
};

//==========================================
//Method to display movements of an account
const displayMovements = function (account) {
  //Clear the movements conatiner
  containerMovements.innerHTML = "";

  //Add movement rows to movements container
  account.movements.forEach(function (movement, i) {
    const movementType = movement > 0 ? "deposit" : "withdrawal";

    //Get the date for the current movement
    let currMovementDate;
    for (const [key, val] of [...account.movementsDates.entries()]) {
      if (val.id === movement) {
        currMovementDate = val.date;
        break;
      }
    }
    const dateString = formatCurrDate(currMovementDate);
    const formattedMovement = formatCurrency(movement);

    //Create the current movement element
    const rowHTML = `
    <div class="movements__row">
        <div class="movements__type movements__type--${movementType}">${
      i + 1
    } ${movementType}</div>
    <div class="movements__date">${dateString}</div>
        <div class="movements__value">${formattedMovement}</div>
    </div>
    `;

    containerMovements.insertAdjacentHTML("afterbegin", rowHTML);
  });
};

//==============================
//Calculate and display balance
const calcAndDisplayBalance = function (currAccount) {
  const sum = currAccount.movements.reduce(function (acc, movement) {
    return acc + movement;
  }, 0);

  labelBalance.textContent = formatCurrency(sum);
  currAccount.balance = sum;
};

//===========================================
//Calculate and display summary of movements
const calcAndDisplaySummary = function (currAcc) {
  //Calculate and display deposits only
  let depositSum = 0;
  try {
    depositSum = currAcc.movements
      .filter((mov) => mov > 0)
      .reduce((acc, mov) => acc + mov);
  } catch (e) {}

  labelSumIn.textContent = formatCurrency(depositSum);

  //Calculate and display withdrawals only
  let withdrawalsSum = 0;
  try {
    withdrawalsSum = currAcc.movements
      .filter((mov) => mov < 0)
      .reduce((acc, mov) => acc + mov);
  } catch (e) {}

  labelSumOut.textContent = formatCurrency(Math.abs(withdrawalsSum));

  //Calculate and display interests gained on deposits
  const interestRateInPercents = currAcc.interestRate;
  const totalInterestReceived = currAcc.movements
    .filter((mov) => mov > 0)
    .map((mov) => mov * (interestRateInPercents / 100))
    .filter((interest) => interest >= 1)
    .reduce((sum, interest) => sum + interest);

  labelSumInterest.textContent = formatCurrency(totalInterestReceived);
};

//==============================
//Method to update the whole UI
const updateUI = function (currAcc) {
  //Display movements
  displayMovements(currAcc);

  //Calculate and display balance
  calcAndDisplayBalance(currAcc);

  //Display summary
  calcAndDisplaySummary(currAcc);
};

//====================================
//Method to create username from name
const createUsername = function (name) {
  const username = name
    .toLowerCase()
    .split(/\s+/)
    .map((name) => name[0])
    .join("");

  return username;
};

//Create usernames for all accounts
accounts.forEach(function (acc) {
  acc.username = createUsername(acc.owner);
});

//============
//Login logic
//Logout method to update the UI
const logOut = function () {
  containerApp.style.opacity = 0;
  inputCloseUsername.value = inputClosePin.value = "";
  labelWelcome.textContent = "Log in to get started";
};

//LogOut timer functionality
const startLogOutTimer = function () {
  let timeInSeconds = 120;

  const tick = function () {
    const min = String(Math.trunc(timeInSeconds / 60)).padStart(2, 0);
    const sec = String(timeInSeconds % 60).padStart(2, 0);

    labelTimer.textContent = `${min}:${sec}`;

    //If timeInSeconds reaches 0, then logOut
    if (timeInSeconds === 0) {
      clearInterval(timer);
      logOut();
    }

    timeInSeconds--;
  };

  //Display the time remaining on the UI every second
  tick();
  const timer = setInterval(tick, 1000);

  return timer;
};

let currAccount, timer;
//Event handler for the "login" button
btnLogin.addEventListener("click", function (e) {
  e.preventDefault();

  //Get user inputs
  const inputUser = inputLoginUsername.value;
  const inputPin = Number(inputLoginPin.value);

  //Get account based on user inputs
  currAccount = accounts.find((acc) => acc.username === inputUser);

  //Check if password is correct. If correct then log in
  if (currAccount?.pin === inputPin) {
    //Show message
    labelWelcome.textContent = `Welcome back, ${
      currAccount.owner.split(/\s+/)[0]
    }`;

    //Remove user accounts information from starting screen
    document.querySelector(".userAccountsData").style.display = "none";

    //Display UI
    containerApp.style.opacity = 100;

    //Display date under "Current balance"
    const currDate = new Date();

    const options = {
      day: "numeric",
      month: "long",
      year: "numeric",
      hour: "numeric",
      minute: "numeric",
    };

    labelDate.textContent = new Intl.DateTimeFormat(locale, options).format(
      currDate
    );

    //Update UI
    updateUI(currAccount);
  }

  //Clear input fields
  inputLoginUsername.value = "";
  inputLoginPin.value = "";
  inputLoginPin.blur();

  //Start logOut timer
  if (timer) clearInterval(timer);
  timer = startLogOutTimer();
});

//=====================
//Transfer money logic
//Transfer button event listener
btnTransfer.addEventListener("click", function (e) {
  e.preventDefault();

  const accountTransferTo = accounts.find(
    (acc) => acc.username === inputTransferTo.value
  );
  const transferAmount = Number(inputTransferAmount.value);

  inputTransferTo.value = inputTransferAmount.value = "";

  if (
    transferAmount > 0 &&
    transferAmount <= currAccount.balance &&
    accountTransferTo &&
    accountTransferTo.username !== currAccount.username
  ) {
    //The transfer logic
    //Make payment
    currAccount.movements.push(-transferAmount);
    //Add the movement date to the movements array
    currAccount.movementsDates.push({
      id: -transferAmount,
      date: new Date().toString(),
    });

    //Receive payment
    accountTransferTo.movements.push(transferAmount);
    //Add the movement date to the movements array
    accountTransferTo.movementsDates.push({
      id: transferAmount,
      date: new Date().toString(),
    });

    //Update UI
    updateUI(currAccount);

    //Reset timer
    clearInterval(timer);
    timer = startLogOutTimer();
  }
});

//====================
//Close account logic
//Close button event handler
btnClose.addEventListener("click", function (e) {
  e.preventDefault();

  const closeUsername = inputCloseUsername.value;
  const closePin = Number(inputClosePin.value);

  if (closeUsername === currAccount.username && closePin === currAccount.pin) {
    //Find index of account to be closed
    const index = accounts.findIndex((acc) => acc.username === closeUsername);
    console.log(index);

    //Remove account from the array
    accounts.splice(index, 1);

    //Update UI
    document.querySelector(".userAccountsData").style.display = "block";
    document.getElementById(
      `userAccountsData.${closeUsername}`
    ).style.visibility = "hidden";
    logOut();

    //Clear timer
    clearInterval(timer);
  }
});

//===================
//Request loan logic
btnLoan.addEventListener("click", function (e) {
  e.preventDefault();

  const loanAmount = Math.floor(inputLoanAmount.value);

  if (
    loanAmount > 0 &&
    currAccount.movements.some((mov) => mov >= 0.1 * loanAmount)
  ) {
    //It will take 2 seconds to approve the loan
    setTimeout(function () {
      //Add the loan to the movements array
      currAccount.movements.push(loanAmount);
      //Add the movement date to the movements array
      currAccount.movementsDates.push({
        id: loanAmount,
        date: new Date().toString(),
      });

      //Update UI
      updateUI(currAccount);

      //Reset timer
      clearInterval(timer);
      timer = startLogOutTimer();
    }, 2000);
  }

  inputLoanAmount.value = "";
});

//====================
//Sort movemets logic
let isSorted = false;
btnSort.addEventListener("click", function (e) {
  e.preventDefault();

  if (!isSorted) {
    //Sort arr in ascending order
    const accCopy = JSON.parse(JSON.stringify(currAccount));
    accCopy.movements.sort((a, b) => a - b);
    isSorted = true;

    //Display movements in sorted way
    displayMovements(accCopy);
  } else {
    isSorted = false;

    //Display movements in original way
    displayMovements(currAccount);
  }
});
