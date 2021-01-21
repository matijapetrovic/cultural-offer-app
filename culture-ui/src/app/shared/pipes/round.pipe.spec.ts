import { RoundPipe } from './round.pipe';

describe('RoundPipe', () => {
  const pipe = new RoundPipe();
  it('create an instance', () => {
    expect(pipe).toBeTruthy();
  });

  it('transforms 19.123 to be 19', () => {
    expect(pipe.transform(19.123)).toBe('19');
  });

  it('transforms 19.802 to be 20', () => {
    expect(pipe.transform(19.802)).toBe('20');
  });
});
