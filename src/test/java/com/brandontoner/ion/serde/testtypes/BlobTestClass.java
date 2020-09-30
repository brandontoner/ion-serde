package com.brandontoner.ion.serde.testtypes;

import java.util.Arrays;

public class BlobTestClass {
    private final byte[] aBlob;

    public BlobTestClass(final byte[] aBlob) {
        this.aBlob = aBlob;
    }

    public byte[] getABlob() {
        return aBlob;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlobTestClass)) {
            return false;
        }
        BlobTestClass that = (BlobTestClass) o;
        return Arrays.equals(aBlob, that.aBlob);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(aBlob);
    }

    @Override
    public String toString() {
        return "BlobClobTestClass{" + "aBlob=" + Arrays.toString(aBlob) + '}';
    }
}
