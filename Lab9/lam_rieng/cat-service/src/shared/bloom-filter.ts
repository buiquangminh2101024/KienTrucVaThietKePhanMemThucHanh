import { BloomFilter } from 'bloom-filters';

// create a Bloom Filter with a capacity of 1000 elements and error rate of 1%
export const catBloomFilter = BloomFilter.create(1000, 0.01);
