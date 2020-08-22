init(N) :- preprocess(N, 2), !.

preprocess(N, Current) :-
	Next is Current * Current, 
	Next > N, !.
	
preprocess(N, Current) :- 
	\+ prime_divisors_list(Current, _),
	Square is Current * Current, 
	eratosthenes_sieve(N, Square, Current), 
	Next is Current + 1, 
	preprocess(N, Next).

	
preprocess(N, Current) :- 
	prime_divisors_list(Current, Some_Result),
	Next is Current + 1,
	preprocess(N, Next).

eratosthenes_sieve(N, Current, Step) :- Current > N, !.

eratosthenes_sieve(N, Current, Step) :- 
	assert(prime_divisors_list(Current, Step)), 
	Next is Current + Step, 
	eratosthenes_sieve(N, Next, Step).

prime(N) :- N > 1, \+ prime_divisors_list(N, _), !.

composite(N) :- \+ prime(N), !.

%==========================================================

prime_divisors(1, []) :- !.

prime_divisors(N, [N]) :- 
	number(N),
	prime(N), !.
	
prime_divisors(N, List) :- 
	number(N), 
	get_divisors_list(N, 1, List), !.

get_divisors_list(1, _, []) :- !.

get_divisors_list(N, _, [N]) :- prime(N), !.

get_divisors_list(N, Last_Divisor, [H | T]) :- 
	number(N),
	N > 1,
	prime_divisors_list(N, H),
	Last_Divisor =< H, 
	Left is div(N, H),
	get_divisors_list(Left, H, T).

get_divisors_list(N, N, _, []) :- !.

get_divisors_list(N, Current_Result, Last_Divisor, [H | T]) :- 
	Last_Divisor =< H, 
	prime(H), 
	New_Result is Current_Result * H, 
	get_divisors_list(N, New_Result, H, T).


prime_divisors(N, List) :- 
	\+ number(N),
	get_divisors_list(N, 1, 1, List), !.



prime_palindrome(Number, System) :- 
	prime(Number), 
	reverse_translate(Number, System, Result), 
	reverse(Result, Result), !.

reverse_translate(0, System, []) :- !.

reverse_translate(Number, System, [H | T]) :- 
	H is mod(Number, System), 
	Next_Number is div(Number, System), 
	reverse_translate(Next_Number, System, T).