export function prepareEnumForServer(object: any, fieldName: string, enumType: any): void {
  object[fieldName] = Object.keys(enumType).find((val) => {
    return enumType[val] === object[fieldName];
  });
}

export function processEnumFromServer(fieldName: string, enumType: any) {
  return (object: any): any => {
    object[fieldName] = enumType[object[fieldName]];
    return object;
  };
}
