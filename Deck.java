import java.util.*;

class Card {
    String suit, rank;
    Card(String s, String r) { suit = s; rank = r; }
    public String toString() { return rank + suit; }
    public boolean equals(Object o) {
        Card c = (Card)o;
        return suit.equals(c.suit) && rank.equals(c.rank);
    }
}

class CardDeck {
    private List<Card> deck = new ArrayList<>();
    private Set<Card> dealt = new HashSet<>();
    
    CardDeck() {
        String[] suits = {"♠", "♥", "♦", "♣"};
        String[] ranks = {"2","3","4","5","6","7","8","9","10","J","Q","K","A"};
        for (String s : suits) for (String r : ranks) deck.add(new Card(s, r));
    }
    
    void shuffle() { Collections.shuffle(deck); }
    
    Card deal() {
        if (deck.isEmpty()) throw new RuntimeException("Колода пуста");
        Card card = deck.remove(deck.size()-1);
        dealt.add(card);
        return card;
    }
    
    void returnCard(Card card) {
        if (!dealt.contains(card)) throw new RuntimeException("Карта не раздана");
        if (deck.contains(card)) throw new RuntimeException("Карта уже в колоде");
        dealt.remove(card);
        deck.add(card);
    }
    
    void show() {
        System.out.print("Колода (" + deck.size() + "): ");
        for (Card c : deck) System.out.print(c + " ");
        System.out.println();
    }
}

public class SimpleDeck {
    public static void main(String[] args) {
        CardDeck deck = new CardDeck();
        deck.shuffle();
        deck.show();
        
        Card c1 = deck.deal();
        Card c2 = deck.deal();
        System.out.println("Раздали: " + c1 + " и " + c2);
        deck.show();
        
        deck.returnCard(c1);
        System.out.println("Вернули " + c1);
        deck.show();
    }
}
