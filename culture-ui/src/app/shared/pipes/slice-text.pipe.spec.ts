import { SliceTextPipe } from './slice-text.pipe';

describe('SliceTextPipe', () => {
  const pipe = new SliceTextPipe();
  it('create an instance', () => {
    expect(pipe).toBeTruthy();
  });

  it('transforms abcd with length 5 to abcd', () => {
    expect(pipe.transform('abcd', 5)).toBe('abcd');
  });

  it('transforms abcdef with length 5 to abcde...', () => {
    expect(pipe.transform('abcdef', 5)).toBe('abcde...');
  });
});
