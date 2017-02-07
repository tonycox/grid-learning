package io.tonycox.grid;

/**
 * @author Anton Solovev
 * @since 07.02.17.
 */
public class SharedClass {

	private int ingerType;
	final String finalType;
	transient String transType;

	public SharedClass(String finalType) {
		this.finalType = finalType;
	}

	public int getIngerType() {
		return ingerType;
	}

	public void setIngerType(int ingerType) {
		this.ingerType = ingerType;
	}

	@Override
	public String toString() {
		return "SharedClass{" +
				"ingerType='" + ingerType + '\'' +
				", finalType='" + finalType + '\'' +
				", transType='" + transType + '\'' +
				'}';
	}
}
