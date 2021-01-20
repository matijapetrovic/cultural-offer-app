import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'localDate'
})
export class LocalDatePipe implements PipeTransform {

  transform(value: number[], withTime: boolean): any {
    let result: string = value.slice(0, 3).reverse().map((num) => num+'').join('/');
    if (withTime)
      result += ' ' + value.slice(3, 6).map((num) => (num < 10 ? '0'+num : num)+'').join(':');
    return result;
  }

}
