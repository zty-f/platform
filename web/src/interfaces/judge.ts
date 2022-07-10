export interface Team {
  id: number,
  teamId: number,
  judgeId: number,
  judged: boolean,
  projectPath: string,
  score: number,
}

export interface TeamUpdateForm {
  id: number,
  score: number,
}
