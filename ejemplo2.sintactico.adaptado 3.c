int ~ suma <| int ~ a, float ~ b |> |:
    return ~ a !
:|

float ~ division <| float ~ x, float ~ y |> |:
    return ~ x !
:|

bool ~ esMayor <| int ~ n1, int ~ n2 |> |:
    return ~ greather_t <| n1, n2 |> !
:|

empty ~ __main__ ~ <| |> |:

    ¡¡ Variables basicas
    int ~ x <- 5 !
	float ~ pi <- 3.14 !

    ¡¡ Arreglo correcto
    int ~ matriz <<2>> <<2>> <- |: |: 1, 2 :| , |: 3, 4 :| :| !

    ¡¡ Llamadas a funciones correctas
    int ~ r1 <- suma <| 5, 3.14 |> !
    bool ~ r2 <- esMayor <| x, 3 |> !

	matriz <<2>> <<6>> <- 5!

:|