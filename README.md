# Java ArrayList element removal techniques benchmark

This repo performs a timing benchmark of various methods to remove every second element from an ArrayList.

(Spoiler: removing elements from an ArrayList is slow! If you need to frequently add/remove elements in the middle of a list, consider using a [LinkedList](https://docs.oracle.com/javase/8/docs/api/java/util/LinkedList.html) instead)

Stackoverflow Question: [Removing every other element in an array list](https://stackoverflow.com/questions/61845242/removing-every-other-element-in-an-array-list/61845315)


# Method

We generate an ArrayList containing 100,003 lines:
```
{
  "line 0",
  "line 1,
  ...
  "line 99999",
  "duplicate line",
  "duplicate line",
  "duplicate line"
}
```

We then test various methods proposed in the Stack Exchange Question to remove every second element.

(Due to ambiguity, most answers are designed to remove the even elements and keep the odds, but one removes odds and keep evens, and another (originally marked best answer) discards all the duplicate lines regardless of whether they are odd or even!).


# Running it

```
javac Main.java
java Main
```


# Results

```
removeUsingRemoveAll  took 15018 milliseconds
removeUsingIter       took   216 milliseconds
removeFromEnd         took    94 milliseconds
removeUsingIterLinked took    12 milliseconds
removeUsingSecondList took     3 milliseconds
```

# Contributing

I'm open to PRs if you would like to add your own method.

