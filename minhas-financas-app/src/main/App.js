import React from 'react';
import { render } from '@testing-library/react';

import Rotas from './rotas'
import Navbar from '../components/navbar'

import 'bootswatch/dist/flatly/bootstrap.css'
import '../custom.css'

//retorna jsx
/*function App() {
  return (
    <div>
      Hello World!
      Hello {this.state.nome}
    </div>
  );
}*/
class App extends React.Component {
  
  render(){
    return(
      <>
        <Navbar />
        <div className="container">
            <Rotas />
        </div>
      </>
    )
  }

}

export default App
