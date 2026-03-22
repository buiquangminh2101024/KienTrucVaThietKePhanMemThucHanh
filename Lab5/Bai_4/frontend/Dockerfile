# Chạy Vite dev server
FROM node:20
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .

RUN apt-get update && apt-get install -y curl

EXPOSE 5173
CMD ["npm", "run", "dev"]