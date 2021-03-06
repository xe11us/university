# Курс "Введение в программирование"
## Язык Java
## Первый семестр



# Условия задач:	

## [Домашнее задание 13. Обработка ошибок](https://github.com/xe11us/university/tree/master/programming/second-semester/java-expression)

1.  Добавьте в программу вычисляющую выражения обработку ошибок, в том числе:
    *   ошибки разбора выражений;
    *   ошибки вычисления выражений.
2.  Для выражения `1000000*x*x*x*x*x/(x-1)` вывод программы должен иметь следующий вид:

    <pre>x       f
    0       0
    1       division by zero
    2       32000000
    3       121500000
    4       341333333
    5       overflow
    6       overflow
    7       overflow
    8       overflow
    9       overflow
    10      overflow
                </pre>

    Результат `division by zero` (`overflow`) означает, что в процессе вычисления произошло деление на ноль (переполнение).
3.  При выполнении задания следует обратить внимание на дизайн и обработку исключений.
4.  Человеко-читаемые сообщения об ошибках должны выводится на консоль.
5.  Программа не должна «вылетать» с исключениями (как стандартными, так и добавленными).

Модификации
 * *Базовая*
    * Класс `ExpressionParser` должен реализовывать интерфейс
        Parser
    * Классы `CheckedAdd`, `CheckedSubtract`, `CheckedMultiply`,
        `CheckedDivide` и `CheckedNegate` должны реализовывать интерфейс TripleExpression
    * Нельзя использовать типы `long` и `double`
    * Нельзя использовать методы классов `Math` и `StrictMath`
 * *PowLog2* (простая)
    * Дополнительно реализуйте унарные операции:
        * `log2` – логарифм по уснованию 2, `log2 10` равно 3;
        * `pow2` – два в степени, `pow2 4` равно 16.


## [Домашнее задание 12. Разбор выражений](https://github.com/xe11us/university/tree/master/programming/second-semester/java-expression)

1.  Доработайте предыдущее домашнее задание, так что бы выражение строилось по записи вида

    <pre>x * (x - 2)*x + 1</pre>

2.  В записи выражения могут встречаться: умножение `*`, деление `/`, сложение `+`, вычитание `-`, унарный минус `-`, целочисленные константы (в десятичной системе счисления, которые помещаются в 32-битный знаковый целочисленный тип), круглые скобки, переменные (`x`) и произвольное число пробельных символов в любом месте (но не внутри констант).
3.  Приоритет операторов, начиная с наивысшего
    1.  унарный минус;
    2.  умножение и деление;
    3.  сложение и вычитание.
4.  Разбор выражений рекомендуется производить [методом рекурсивного спуска](https://ru.wikibooks.org/wiki/%D0%A0%D0%B5%D0%B0%D0%BB%D0%B8%D0%B7%D0%B0%D1%86%D0%B8%D0%B8_%D0%B0%D0%BB%D0%B3%D0%BE%D1%80%D0%B8%D1%82%D0%BC%D0%BE%D0%B2/%D0%9C%D0%B5%D1%82%D0%BE%D0%B4_%D1%80%D0%B5%D0%BA%D1%83%D1%80%D1%81%D0%B8%D0%B2%D0%BD%D0%BE%D0%B3%D0%BE_%D1%81%D0%BF%D1%83%D1%81%D0%BA%D0%B0). Алгоритм должен работать за линейное время.

Модификации
 * *Базовая*
    * Класс `ExpressionParser` должен реализовывать интерфейс Parser
    * Результат разбора должен реализовывать интерфейс TripleExpression
 * *Shifts*
    * Дополнительно реализуйте бинарные операции:
        * `<<` – сдвиг влево, минимальный приоритет (`1 << 5 + 3` равно `1 << (5 + 3)` равно 256);
        * `>>` – сдвиг вправо, минимальный приоритет (`1024 >> 5 + 3` равно `1024 >> (5 + 3)` равно 4);
 * *ReverseDigits*
    * Реализуйте операции из модификации *Shifts*.
    * Дополнительно реализуйте унарные операции (приоритет как у унарного минуса):
        * `reverse` – число с переставленными цифрами, `reverse -12345` равно -54321;
        * `digits` – сумма цифр числа, `digits -12345` равно 15.
 * *Abs*
    * Реализуйте операции из модификации *Shifts*.
    * Дополнительно реализуйте унарные операции (приоритет как у унарного минуса):
        * `abs` – модуль числа, `abs -5` равно 5;
        * `square` – возведение в квадрат, `square -5` равно 25.


## [Домашнее задание 11. Выражения](https://github.com/xe11us/university/tree/master/programming/first-semester/expressions)

1.  Разработайте классы `Const`, `Variable`, `Add`, `Subtract`, `Multiply`, `Divide` для вычисления выражений с одной переменной.
2.  Классы должны позволять составлять выражения вида

    <pre>new Subtract(
        new Multiply(
            new Const(2),
            new Variable("x")
        ),
        new Const(3)
    ).evaluate(5)
                </pre>

    При вычислении такого выражения вместо каждой переменной подставляется значение, переданное в качестве параметра методу `evaluate` (на данном этапе имена переменных игнорируются). Таким образом, результатом вычисления приведенного примера должно стать число 7.
3.  Метод `toString` должен выдавать запись выражения в полноскобочной форме. Например

    <pre>new Subtract(
        new Multiply(
            new Const(2),
            new Variable("x")
        ),
        new Const(3)
    ).toString()
                </pre>

    должен выдавать `((2 * x) - 3)`.
4.  _Сложный вариант._ Метод `toMiniString` должен выдавать выражение с минимальным числом скобок. Например

    <pre>new Subtract(
        new Multiply(
            new Const(2),
            new Variable("x")
        ),
        new Const(3)
    ).toMiniString()
                </pre>

    должен выдавать `2 * x - 3`.
5.  Реализуйте метод `equals`, проверяющий, что два выражения совпадают. Например,

    <pre>new Multiply(new Const(2), new Variable("x"))
        .equals(new Multiply(new Const(2), new Variable("x")))
                </pre>

    должно выдавать `true`, а

    <pre>new Multiply(new Const(2), new Variable("x"))
        .equals(new Multiply(new Variable("x"), new Const(2)))
                </pre>

    должно выдавать `false`.
6.  Для тестирования программы должен быть создан класс `Main`, который вычисляет значение выражения `x<sup>2</sup>−2x+1`, для `x`, заданного в командной строке.
7.  При выполнение задания следует обратить внимание на:
    *   Выделение общего интерфейса создаваемых классов.
    *   Выделение абстрактного базового класса для бинарных операций.

Модификации
 * *Базовая*
    * Реализуйте интерфейс Expression
        * Запускать c аргументом `easy` или `hard`
 * *Double*
    * Дополнительно реализуйте интерфейс DoubleExpression
 * *Triple*
    * Дополнительно реализуйте интерфейс TripleExpression


## [Домашнее задание 10. Игра n,m,k](https://github.com/xe11us/university/tree/master/programming/first-semester/tic-tac-toe)

1.  Реализуйте [игру m,n,k](https://en.wikipedia.org/wiki/M,n,k-game).
2.  Добавьте обработку ошибок ввода пользователя.
3.  _Простая версия_. Проверку выигрыша можно производить за _O(nmk)_.
4.  _Сложная версия_.
    *   Проверку выигрыша нужно производить за _O(k)_.
    *   Предотвратите жульничество: у игрока не должно быть возможности достать `Board` из `Position`.
5.  _Бонусная версия_. Реализуйте `Winner` — игрок, который выигрывает всегда, когда это возможно.

Модификации
 * *Турнир*
    * Добавьте поддержку кругового турнира из _c_ кругов
    * Выведите таблицу очков по схеме:
        * 3 очка за победу
        * 1 очко за ничью
        * 0 очков за поражение
 * *Multiplayer*
    * Добавьте поддержку значков `-` и `|`
    * Добавьте возможность игры для 3 и 4 игроков
 * *Ромб*
    * Добавить поддержку доски в форме ромба
 * *Матчи*
    * Добавьте поддержку матчей: последовательность игр указанного числа побед
    * Стороны в матче должны меняться каждую игру


## [Домашнее задание 9. Markdown to HTML](https://github.com/xe11us/university/tree/master/programming/first-semester/markdown-to-html)

1.  Разработайте конвертер из [Markdown](https://ru.wikipedia.org/wiki/Markdown)-разметки в [HTML](https://ru.wikipedia.org/wiki/HTML).
2.  Конвертер должен поддерживать следующие возможности:
    1.  Абзацы текста разделяются пустыми строками.
    2.  Элементы строчной разметки: выделение (<tt>*</tt> или <tt>_</tt>), сильное выделение (<tt>**</tt> или <tt>__</tt>), зачеркивание (<tt>--</tt>), код (<tt>`</tt>)
    3.  Заголовки (<tt>#</tt> * уровень заголовка)
3.  Конвертер должен называться <tt>Md2Html</tt> и принимать два аргумента: название входного файла с Markdown-разметкой и название выходного файла c HTML-разметкой. Оба файла должны иметь кодировку UTF-8.
4.  Пример
    *   Входной файл

        <pre># Заголовок первого уровня

        ## Второго

        ### Третьего ## уровня

        #### Четвертого
        # Все еще четвертого

        Этот абзац текста,
        содержит две строки.

            # Может показаться, что это заголовок.
        Но нет, это абзац начинающийся с `#`.

        #И это не заголовок.

        ###### Заголовки могут быть многострочными
        (и с пропуском заголовков предыдущих уровней)

        Мы все любим *выделять* текст _разными_ способами.
        **Сильное выделение**, используется гораздо реже,
        но __почему бы и нет__?
        Немного --зачеркивания-- еще ни кому не вредило.
        Код представляется элементом `code`.

        Обратите внимание, как экранируются специальные
        HTML-символы, такие как `<`, `>` и `&`.

        Знаете ли вы, что в Markdown, одиночные * и _
        не означают выделение?
        Они так же могут быть заэкранированы
        при помощи обратного слэша: \*.

        Лишние пустые строки должны игнорироваться.

        Любите ли вы *вложеные __выделения__* так,
        как __--люблю--__ их я?
                    </pre>

    *   Выходной файл

        <pre><h1>Заголовок первого уровня</h1>
        <h2>Второго</h2>
        <h3>Третьего ## уровня</h3>
        <h4>Четвертого
        # Все еще четвертого</h4>
        <p>Этот абзац текста,
        содержит две строки.</p>
        <p>    # Может показаться, что это заголовок.
        Но нет, это абзац начинающийся с <code>#</code>.</p>
        <p>#И это не заголовок.</p>
        <h6>Заголовки могут быть многострочными
        (и с пропуском заголовков предыдущих уровней)</h6>
        <p>Мы все любим <em>выделять</em> текст <em>разными</em> способами.
        <strong>Сильное выделение</strong>, используется гораздо реже,
        но <strong>почему бы и нет</strong>?
        Немного <s>зачеркивания</s> еще ни кому не вредило.
        Код представляется элементом <code>code</code>.</p>
        <p>Обратите внимание, как экранируются специальные
        HTML-символы, такие как <code>&lt;</code>, <code>&gt;</code> и <code>&amp;</code>.</p>
        <p>Знаете ли вы, что в Markdown, одиночные * и _
        не означают выделение?
        Они так же могут быть заэкранированы
        при помощи обратного слэша: *.</p>
        <p>Лишние пустые строки должны игнорироваться.</p>
        <p>Любите ли вы <em>вложеные <strong>выделения</strong></em> так,
        как <strong><s>люблю</s></strong> их я?</p>
                    </pre>

    *   Реальная разметка

        # Заголовок первого уровня

        ## Второго

        ### Третьего ## уровня

        #### Четвертого # Все еще четвертого

        Этот абзац текста, содержит две строки.

        # Может показаться, что это заголовок. Но нет, это абзац начинающийся с `#`.

        #И это не заголовок.

        ###### Заголовки могут быть многострочными (и с пропуском заголовков предыдущих уровней)

        Мы все любим _выделять_ текст _разными_ способами. **Сильное выделение**, используется гораздо реже, но **почему бы и нет**? Немного <s>зачеркивания</s> еще ни кому не вредило. Код представляется элементом `code`.

        Обратите внимание, как экранируются специальные HTML-символы, такие как `<`, `>` и `&`.

        Знаете ли вы, что в Markdown, одиночные * и _ не означают выделение? Они так же могут быть заэкранированы при помощи обратного слэша: *.

        Лишние пустые строки должны игнорироваться.

        Любите ли вы _вложеные **выделения**_ так, как **<s>люблю</s>** их я?

Модификации
 * *Underline*
    * Добавьте поддержку `++подчеркивания++`: `<u>подчеркивания</u>`
 * *Link*
    * Добавьте поддержку ```[ссылок с _выделением_](https://kgeorgiy.info)```:
        ```&lt;a href='https://kgeorgiy.info'>ссылок с &lt;em>выделением&lt;/em>&lt;/a>```
 * *Mark*
    * Добавьте поддержку `~выделения цветом~`: `<mark>выделения цветом</mark>`
 * *Image*
    * Добавьте поддержку ```![картинок](http://www.ifmo.ru/images/menu/small/p10.jpg)```:
        ```&lt;img alt='картинок' src='http://www.ifmo.ru/images/menu/small/p10.jpg'&gt;```


## [Домашнее задание 7. Разметка](https://github.com/xe11us/university/tree/master/programming/first-semester/markdown)

1.  Разработайте набор классов для текстовой разметки.
2.  Класс <tt>Paragraph</tt> может содержать произвольное число других элементов разметки и текстовых элементов.
3.  Класс <tt>Text</tt> – текстовый элемент.
4.  Классы разметки <tt>Emphasis</tt>, <tt>Strong</tt>, <tt>Strikeout</tt> – выделение, сильное выделение и зачеркивание. Элементы разметки могут содержать произвольное число других элементов разметки и текстовых элементов.
5.  Все классы должны реализовывать метод <tt>toMarkdown([StringBuilder](https://docs.oracle.com/javase/8/docs/api/java/lang/StringBuilder.html))</tt>, которой должен генерировать [Markdown](https://ru.wikipedia.org/wiki/Markdown)-разметку по следующим правилам:
    1.  текстовые элементы выводятся как есть;
    2.  выделенный текст окружается символами '<tt>*</tt>';
    3.  сильно выделенный текст окружается символами '<tt>__</tt>';
    4.  зачеркнутый текст окружается символами '<tt>~</tt>'.
6.  Следующий код должен успешно компилироваться:

    <pre>    Paragraph paragraph = new Paragraph(List.of(
            new Strong(List.of(
                new Text("1"),
                new Strikeout(List.of(
                    new Text("2"),
                    new Emphasis(List.of(
                        new Text("3"),
                        new Text("4")
                    )),
                    new Text("5")
                )),
                new Text("6")
            ))
        ));
    </pre>

    Вызов <tt>paragraph.toMakdown(new StringBuilder())</tt> должен заполнять переданный <tt>StringBuilder</tt> следующим содержимым:

    <pre>    __1~2*34*5~6__
    </pre>

7.  Разработанные классы должны находиться в пакете <tt>markup</tt>.

Модификации
 * *HTML*
    * Дополнительно реализуйте метод `toHtml`, генерирующий HTML-разметку:
      * выделеный текст окружается тегом `em`;
      * сильно выделеный текст окружается тегом `strong`;
      * зачеркнутый текст окружается тегом `s`.
 * *HTML списки*
    * Добавьте поддержку:
      * Нумерованных списков (класс `OrderedList`, тег `ol`): последовательность элементов
      * Ненумерованных списков (класс `UnorderedList`, тег `ul`): последовательность элементов
      * Элементов списка (класс `ListItem`, тег `li`): последовательность абзацев и списков
    * Для новых классов поддержка Markdown не требуется
 * *TeX*
    * Дополнительно реализуйте метод `toTex`, генерирующий TeX-разметку:
      * выделеный текст заключается в `\emph{` и `}`;
      * сильно выделеный текст заключается в `\textbf{` и `}`;
      * зачеркнутый текст заключается в `\textst{` и `}`.
 * *Tex списки*
    * Добавьте поддержку:
      * Нумерованных списков (класс `OrderedList`, окружение `enumerate`): последовательность элементов
      * Ненумерованных списков (класс `UnorderedList`, окружение `itemize`): последовательность элементов
      * Элементов списка (класс `ListItem`, тег `\item`: последовательность абзацев и списков
    * Для новых классов поддержка Markdown не требуется


## [Домашнее задание 6. Подсчет слов++](https://github.com/xe11us/university/tree/master/programming/first-semester/word-stat-scanner)

1.  Разработайте класс `WordStatIndex`, который будет подсчитывать статистику встречаемости слов во входном файле.
2.  Словом называется непрерывная последовательность букв, апострофов и тире (Unicode category Punctuation, Dash). Для подсчета статистики, слова приводятся к нижнему регистру.
3.  Выходной файл должен содержать все различные слова, встречающиеся во входном файле, в порядке их появления. Для каждого слова должна быть выведена одна строка, содержащая слово, число его вхождений во входной файл и номера вхождений этого слова среди всех слов во входном файле.
4.  Имена входного и выходного файла задаются в качестве аргументов командной строки. Кодировка файлов: UTF-8.
5.  Программа должна работать за линейное от размера входного файла время.
6.  Для реализации программы используйте Collections Framework.
7.  _Сложный вариант._ Реализуйте и примените класс `IntList`, компактно хранящий список целых чисел.
8.  Примеры работы программы:
    *   Входной файл:

        <pre>    To be, or not to be, that is the question:
        </pre>

        Выходной файл:

        <pre>    to 2 1 5
            be 2 2 6
            or 1 3
            not 1 4
            that 1 7
            is 1 8
            the 1 9
            question 1 10
        </pre>

    *   Входной файл:

        <pre>    Monday's child is fair of face.
            Tuesday's child is full of grace.
        </pre>

        Выходной файл:

        <pre>    monday's 1 1
            child 2 2 8
            is 2 3 9
            fair 1 4
            of 2 5 11
            face 1 6
            tuesday's 1 7
            full 1 10
            grace 1 12
        </pre>

    *   Входной файл:

        <pre>    Шалтай-Болтай
            Сидел на стене.
            Шалтай-Болтай
            Свалился во сне.
        </pre>

        Выходной файл:

        <pre>    шалтай-болтай 2 1 5
            сидел 1 2
            на 1 3
            стене 1 4
            свалился 1 6
            во 1 7
            сне 1 8
        </pre>

Модификации
 * *LineIndex*
    * В выходном файле слова должны быть упорядочены в лексикографическом порядке
    * Вместо номеров вхождений во всем файле надо указывать
      `<номер строки>:<номер в строке>`
    * Класс должен иметь имя `WordStatLineIndex`
 * *FirstIndex*
    * Вместо номеров вхождений во всем файле надо указывать
      только первое вхождение в каждой строке
    * Класс должен иметь имя `WordStatFirstIndex`
 * *LastIndex*
    * Вместо номеров вхождений во всем файле надо указывать
      только последнее вхождение в каждой строке
    * Класс должен иметь имя `WordStatLastIndex`
 * *SortedLastIndex*
    * В выходном файле слова должны быть упорядочены в лексикографическом порядке
    * Вместо номеров вхождений во всем файле надо указывать
      только последнее вхождение в каждой строке
    * Класс должен иметь имя `WordStatSortedLastIndex`


## [Домашнее задание 5. Свой сканнер](https://github.com/xe11us/university/blob/master/programming/first-semester/word-stat-scanner/MyScanner.java)

1.  Реализуйте свой аналог класса `Scanner` на основе `Reader`.
2.  Примените разработанный `Scanner` для решения задания «Реверс».
3.  Примените разработанный `Scanner` для решения задания «Статистика слов».
4.  Код, управляющий чтением должен быть общим.
5.  _Сложный вариант_. Код, выделяющий числа и слова должен быть общим.

Модификации
 * *Transpose*
    * Рассмотрим входные данные как (не полностью определенную) матрицу,
      выведите ее в транспонированном виде
    * Класс должен иметь имя `ReverseTranspose`
 * *Sort*
    * Строки должны быть отсортированы по сумме в обратном порядке
      (при равенстве сумм – в порядке обратном следованию во входе).
      Числа в строке так же должны быть отсортированы в обратном порядке.
 * *Min*
    * Рассмотрим входные данные как (не полностью определенную) матрицу,
      вместо каждого числа выведите минимум из чисел в его столбце и строке
    * Класс должен иметь имя `ReverseMin`


## [Домашнее задание 4. Подсчет слов](https://github.com/xe11us/university/tree/master/programming/first-semester/word-stat-scanner)

1.  Разработайте класс `WordStat`, который будет подсчитывать статистику встречаемости слов во входном файле.
2.  Словом называется непрерывная последовательность букв, апострофов и тире (Unicode category Punctuation, Dash). Для подсчета статистики, слова приводятся к нижнему регистру.
3.  Выходной файл должен содержать все различные слова, встречающиеся во входном файле, в порядке их появления. Для каждого слова должна быть выведена одна строка, содержащая слово и число его вхождений во входной файл.
4.  Имена входного и выходного файла задаются в качестве аргументов командной строки. Кодировка файлов: UTF-8.
5.  Примеры работы программы:
    *   Входной файл:

        <pre>    To be, or not to be, that is the question:
        </pre>

        Выходной файл:

        <pre>    to 2
            be 2
            or 1
            not 1
            that 1
            is 1
            the 1
            question 1
        </pre>

    *   Входной файл:

        <pre>    Monday's child is fair of face.
            Tuesday's child is full of grace.
        </pre>

        Выходной файл:

        <pre>    monday's 1
            child 2
            is 2
            fair 1
            of 2
            face 1
            tuesday's 1
            full 1
            grace 1
        </pre>

    *   Входной файл:

        <pre>    Шалтай-Болтай
            Сидел на стене.
            Шалтай-Болтай
            Свалился во сне.
        </pre>

        Выходной файл:

        <pre>    шалтай-болтай 2
            сидел 1
            на 1
            стене 1
            свалился 1
            во 1
            сне 1
        </pre>

Модификации
 * *Words*
    * В выходном файле слова должны быть упорядочены в лексикографическом порядке
    * Класс должен иметь имя `WordStatWords`
 * *Sort*
    * Пусть _n_ – число слов во входном файле,
      тогда программа должна работать за O(_n_ log _n_).
 * *Count*
    * В выходном файле слова должны быть упорядочены по возрастанию числа
      вхождений, а при равном числе вхождений – по порядку первого вхождения
      во входном файле
    * Класс должен иметь имя `WordStatCount`


## [Домашнее задание 3. Реверс](https://github.com/xe11us/university/tree/master/programming/first-semester/reverse)

1.  Разработайте класс `Reverse`, читающий числа из стандартного входа, и выводящий их на стандартный вывод в обратном порядке.
2.  В каждой строке входа содержится некоторое количество целых чисел (может быть 0). Числа разделены пробелами. Каждое число помещается в тип `int`.
3.  Порядок строк в выходе должен быть обратным по сравнению с порядком строк во входе. Порядок чисел в каждой строке так же должен быть обратным к порядку чисел во входе.
4.  Вход содержит не более 10<sup>6</sup> чисел.
5.  Для чтения чисел используйте класс [Scanner](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Scanner.html)
6.  Примеры работы программы:
    *   Вход:

        <pre>1 2
        3
        </pre>

        Выход:

        <pre>3
        2 1
        </pre>

    *   Вход:

        <pre>1

        2 -3
        </pre>

        Выход:

        <pre>-3 2

        1
        </pre>

Модификации
 * *Even*
    * Выведите только четные числа (в реверсивном порядке)
    * Класс должен иметь имя `ReverseEven`
 * *Linear*
    * Пусть _n_ – сумма числа чисел и строк во входе,
      тогда программе разрешается потратить не более 5_n_+O(1) памяти
 * *Sum*
    * Рассмотрим входные данные как (не полностью определенную) матрицу,
      вместо каждого числа выведите сумму чисел в его столбце и строке
    * Класс должен иметь имя `ReverseSum`


## [Домашнее задание 2. Сумма чисел](https://github.com/xe11us/university/tree/master/programming/first-semester/sum)

1.  Разработайте класс `Sum`, который при запуске из командной строки будет складывать переданные в качестве аргументов целые числа и выводить их сумму на консоль.
2.  Примеры запуска программы:

    <dl>

    <dt>`java Sum 1 2 3`</dt>

    <dd>Результат: 6</dd>

    <dt>`java Sum 1 2 -3`</dt>

    <dd>Результат: 0</dd>

    <dt>`java Sum "1 2 3"`</dt>

    <dd>Результат: 6</dd>

    <dt>`java Sum "1 2" " 3"`</dt>

    <dd>Результат: 6</dd>

    <dt>`java Sum " "`</dt>

    <dd>Результат: 0</dd>

    </dl>

    Аргументы могут содержать цифры и произвольные [пробельные символы](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Character.html#isWhitespace(char)).
3.  При выполнении задания можно считать, что для представления входных данных и промежуточных результатов достаточен тип `int`.
4.  При выполнении задания полезно ознакомиться с документацией к классам [String](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/String.html) и [Integer](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Integer.html).

Модификации
  * *Long*
    * Входные данные являются 64-битными целыми числами
    * Класс должен иметь имя `SumLong`
 * *Hex*
    * Входные данные являются 32-битными целыми числами
    * Шестнадцатеричные числа имеют префикс `0x`
    * Класс должен иметь имя `SumHex`
 * *Double*
    * Входные данные являются 64-битными числами с формате с плавающей точкой
    * Класс должен иметь имя `SumDouble`
 * *LongHex*
    * Входные данные являются 64-битными целыми числами
    * Шестнадцатеричные числа имеют префикс `0x`
    * Класс должен иметь имя `SumLongHex`