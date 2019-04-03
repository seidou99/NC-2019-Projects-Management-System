export class Project {
  id: number;
  code: string;
  summary: string;


  constructor(code: string, summary: string) {
    this.code = code;
    this.summary = summary;
  }
}
