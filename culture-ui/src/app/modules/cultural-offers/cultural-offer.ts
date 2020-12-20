export interface CulturalOffer {
  id: number,
  name: string,
  description: string,
  images: Array<string>,
  latitude: number,
  longitude: number,
  subscribed?: boolean
}