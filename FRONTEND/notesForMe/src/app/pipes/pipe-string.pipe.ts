import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'pipeString'
})
export class PipeStringPipe implements PipeTransform {

  transform(value: String, ...args: unknown[]): unknown {
    const regex = /<[^>]*>/g;
    return value.replace(regex, '');
  }
}
