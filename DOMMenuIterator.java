public class DOMMenuIterator 
{
	private String name;
	private String price;
	private String description;
	
	public DOMMenuIterator(String n, String p, String d)
	{
		name = n;
		price = p;
		description = d;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getPrice()
	{
		return this.price;
	}
	public String getDescription()
	{
		return this.description;
	}
	public void printElement()
	{
		System.out.printf("%-15s £%-10s %s", this.name, this.price, this.description);
	}
}

