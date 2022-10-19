fun main(args: Array<String>) {
    // scope функции - функции области видимости
    // позволяют выполнить для некоторого объекта некоторый код в виде Лямбда выражения
    // при вызове такой функции создается временная область видимости,
    // в которой можно обращаться к объекту без его имени
    // let
    val person = Person("Test", null)
    person.email?.let { println(it) }
    // или если только it
    person.email?.let(::println)
    // без let
    if(person.email != null) println(person.email)

    // with принимает объект для которого вызывается функция

    val ivan = Person2("Ivan", null)
    val result = with(ivan) {
        if(email == null)
            email = "ivan@ivan.ru"
        email
    }
    println(result)

    // run - похожа на with, но run реализована как функция расширения

    // В Kotlin методы, определенные внутри класса, называются функциями-членами.
    // Функции-расширения – это методы, определенные за пределами класса.
    //
    // В Kotlin многие методы встроенных классов реализованы как функции-расширения.
    // Это связано с тем, что Kotlin во многом использует java-библиотеки, и разработчикам было проще
    // дополнить их код за пределами классов.
    val obj1 = MyClass(12, 3)
    obj1.action()
    obj1.action2()


    val petr  = Person2("Petr", null)
    val res = petr.run {
        if(email == null)
            email = "petr@petr.ru"
        email
    }
    println(result)

    // еще можно делать проверку на null
    val oleg = Person2("Oleg", null)
    val validRes = oleg.email?.run{"valid"} ?: "Undefined"
    println(validRes) // undef
    // можно просто выполнить лямбда выражения

    val text = run { "Hello" }
    println(text) // Hello
    // или
    run { println("ttt") } // ttt


    // apply - тоже получает объект в качестве параметра
    // на выходе - объект, для которого выполняется функция
    val maria = Person2("Maria", null)
    maria.apply {
        if(email == null)
            email = "maria@maria.ru"
    }
    println(maria.email)

    // также apply можно использовать при построении объекта в виде паттерна Builder

    val alex = Worker()
    alex.age(14)
    alex.name("Alex")
    println(alex)


    // also - аналогична apply, но внутри объект доступен через параметр it
    val olga = Person2("Olga", null)
    olga.also {
        if(it.email == null)
            it.email = "maria@maria.ru"
    }
    println(maria.email)
}

//Функции расширения в Kotlin позволяют нативно реализовать шаблон «декоратор».
// Например, они позволяют вам писать новые функции для класса из сторонней библиотеки,
// которые вы не можете модифицировать.

class MyClass(val a: Int = 0, val b: Int = 0) {
    fun action() {
        println(a + b)
    }
}
//функция расширения
fun MyClass.action2() {
    println(a-b)
}

data class Person2(val name: String, var email: String?)

class Person(_name: String, _email: String?) {
    val name: String
    val email: String?
    init {
        this.name = _name
        this.email = _email
    }

}

// Класс Worker содержит методы, которые устанвливают каждое из свойств класса
// каждый вызывает apply(), которое передает значение свойсту и возвращает текущий объект
data class Worker(var name: String = "", var age: Int = 0, var company: String = "") {
    fun age(_age: Int): Worker = apply { age = _age }
    fun name(_name: String): Worker = apply { name = _name }
}