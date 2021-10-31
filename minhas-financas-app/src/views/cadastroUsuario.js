import React from 'react'

import Card from '../components/card'
import FormGroup from '../components/form-group'

import {withRouter} from 'react-router-dom'

class CadastroUsuario extends React.Component{

    state = {
        nome: '',
        email: '',
        senha: '',
        senhaRepeticao: ''
    }

    cadastrar = () => {
        console.log(this.state)
    }

    cancelar = () => {
        this.props.history.push('/login')
    }

    render(){
        return(
            
            <div className="row">
                <div className="col-lg-12">
                    <div className="bs-component">
                        <div className="container">
                            <Card title="Cadastro de Usuário">
                                <FormGroup label="Nome: *" htmlFor="inputNome"> 
                                    <input type="text" id="inputNome" name="nome" className="form-control" onChange={e => this.setState({nome: e.target.value})}/>
                                </FormGroup>
                                <FormGroup label="E-mail: *" htmlFor="inputEmail">
                                    <input type="email" id="inputEmail" name="email" className="form-control" onChange={e => this.setState({email: e.target.value})} />
                                </FormGroup>
                                <FormGroup label="Senha: *" html="inputSenha">
                                    <input type="password" id="inputSenha" name="senha" className="form-control" onChange={e => this.setState({senha: e.target.value})} />
                                </FormGroup>
                                <FormGroup label="Repita a Senha: *" html="inputRepitaSenha">
                                    <input type="password" id="inputRepitaSenha" name="senhaRepeticao" className="form-control" onChange={e => this.setState({senhaRepeticao: e.target.value})} />
                                </FormGroup>
                                <button onClick={this.cadastrar} type="button" className="btn btn-success">Salvar</button>
                                <button onClick={this.cancelar} type="button" className="btn btn-danger">Cancelar</button>
                            </Card>
                        </div>
                    </div>
                </div>
            </div>
        )
    }

}

export default withRouter(CadastroUsuario)