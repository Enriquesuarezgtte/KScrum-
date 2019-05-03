export interface IPbi {
    id :string,
    projectId: string,
    PbiTitle: string,
    PbiDescription: string,
    responsableId: string,
    statusId: string
}

enum statusId {
    pending = 1,
    processing,
    finalized,
}