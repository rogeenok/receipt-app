import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'orderBy'
})
export class OrderByPipe implements PipeTransform {

  static isString(value: any) {
    return typeof value === 'string' || value instanceof String;
  }

  static caseInsensitiveSort(a: any, b: any) {
    if (OrderByPipe.isString(a) && OrderByPipe.isString(b)) {
      return a.localeCompare(b);
    }
    return a > b ? 1 : -1;
  }

  static getValue(object: any, expression: string[]) {
    for (let i = 0, n = expression.length; i < n; ++i) {
      const k = expression[i];
      if (!(k in object)) {
        return;
      }
      object = object[k];
    }

    return object;
  }

  transform(value: any | any[], expression?: any, reverse?: boolean, isCaseInsensitive: boolean = false): any {
    if (!value) {
      return value;
    }

    if (Array.isArray(value)) {
      return this.sortArray(value, expression, reverse, isCaseInsensitive);
    }
    return value;
  }

  private sortArray(value: any[], expression?: any, reverse?: boolean, isCaseInsensitive?: boolean): any[] {
    const isDeepLink = expression && expression.indexOf('.') !== -1;

    let array: any[] = value.sort((a: any, b: any): number => {
      if (!expression) {
        if (isCaseInsensitive) {
          return OrderByPipe.caseInsensitiveSort(a, b);
        }
        return a > b ? 1 : -1;
      }

      if (!isDeepLink && expression) {
        if (isCaseInsensitive) {
          if (a && b) {
            return OrderByPipe.caseInsensitiveSort(a[expression], b[expression]);
          }
          return OrderByPipe.caseInsensitiveSort(a, b);
        }

        if (a && b) {
          return a[expression] > b[expression] ? 1 : -1;
        }
        return a > b ? 1 : -1;
      }

      if (isCaseInsensitive) {
        return OrderByPipe.caseInsensitiveSort(OrderByPipe.getValue(a, expression), OrderByPipe.getValue(b, expression));
      }

      return OrderByPipe.getValue(a, expression) > OrderByPipe.getValue(b, expression) ? 1 : -1;
    });

    if (reverse) {
      return array.reverse();
    }

    return array;
  }

}
