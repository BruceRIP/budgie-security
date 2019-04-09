package mx.budgie.billers.accounts.mongo.utils;

public enum DigestAlgorithms {

	MD2,
    MD5,
    SHA_1,
    SHA_256,
    SHA_384,
    SHA_512;
	
	public static DigestAlgorithms getDigestAlgorithm(final String digestType) {
		return DigestAlgorithms.valueOf(DigestAlgorithms.class, digestType);
	}
}
