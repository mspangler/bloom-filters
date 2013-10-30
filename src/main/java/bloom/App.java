package bloom;

import java.io.Serializable;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

public class App {
	
	public static void main(String[] args) {
		new App();
	}
	
	public App() {
		
		final BloomFilter<Person> bloom = BloomFilter.create(this.getFunnel(), 4);
		
		bloom.put(new Person(1, "Mark"));
		bloom.put(new Person(2, "Angela"));
		bloom.put(new Person(3, "Mason"));
		bloom.put(new Person(4, "Kira"));
		
		boolean hasMark = bloom.mightContain(new Person(1, "Mark"));
		boolean hasAngela = bloom.mightContain(new Person(2, "Angela"));
		boolean hasMason = bloom.mightContain(new Person(3, "Mason"));
		boolean hasKira = bloom.mightContain(new Person(4, "Kira"));
		boolean hasBob = bloom.mightContain(new Person(5, "Bob"));
		boolean hasJoe = bloom.mightContain(new Person(1, "Joe"));
		
		Preconditions.checkArgument(hasMark);
		Preconditions.checkArgument(hasAngela);
		Preconditions.checkArgument(hasMason);
		Preconditions.checkArgument(hasKira);
		Preconditions.checkArgument(!hasBob);
		Preconditions.checkArgument(!hasJoe);
	}
	
	public Funnel<Person> getFunnel() {
		return new Funnel<Person>() {
			private static final long serialVersionUID = 2475771202170718169L;
			@Override
			  public void funnel(Person person, PrimitiveSink into) {
			    into
			      .putLong(person.id)
			      .putString(person.name, Charsets.UTF_8);
			  }
			};
	}
	
	public class Person implements Serializable {
		
		private static final long serialVersionUID = 3387186223409218934L;
		public long id;
		public String name;
		
		public Person(long id, String name) {
			this.id = id;
			this.name = name;
		}
	}

}
