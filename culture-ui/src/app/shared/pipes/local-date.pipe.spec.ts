import { LocalDatePipe } from './local-date.pipe';

describe('LocalDatePipe', () => {
  const pipe = new LocalDatePipe();
  it('create an instance', () => {
    expect(pipe).toBeTruthy();
  });

  it('transforms [1970, 1, 1] to 1/1/1970', () => {
    expect(pipe.transform([1970, 1, 1], false)).toBe('1/1/1970');
  });

  it('transforms [1970, 1, 1, 12, 0] to 1/1/1970 12:00', () => {
    expect(pipe.transform([1970, 1, 1, 12, 0], true)).toBe('1/1/1970 12:00');
  });
});
