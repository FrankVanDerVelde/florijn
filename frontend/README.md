# EWA - Florijn

## Frontend

### Getting started
to run the website, in the terminal and sure you are in the frontend folder and run `npm install`. Then run `npm run dev` and you should have the website running. 

### Vue 3
This project uses [Vue 3](https://vuejs.org) as Frontend Framework

### Vite
Vue has it's own way to start up a webserver to host your Vue website on `localhost`.
It works fine but we chose to use [Vite](https://vuejs.org), it's way faster, and has handy features.

### Styling

#### CSS
In this codebase there are two ways to style web pages. The first is using regular CSS. In the `src/styles.css` you will find the color from our design system.
You can use them like so: 
```
.container {
  background: var(--primary-500);
}
```
#### Tailwind CSS
In this project you can also use Tailwind CSS. Tailwind can be used right inline with your HTML. It is also integrated with our design system colors.

Quick example:
```
<div class="flex gap-8 items-center bg-primary-500 hover:bg-primary-700"></div>
```
We apply Flexbox with a gap and center the items horizontally, we give it the primary background color, when we hover we apply a darker shade as the background.  


