import React, {Component} from 'react'

class Good extends Component {

    render() {
        return (
            <div className="ui centered card">
                <div className="image">
                    <img src={require('../pizza.jpeg')}/>
                </div>
                <div className="content">
                    <div className="header">{this.props.el.name}</div>
                </div>
                <div className="extra content">
                    <span className="right floated">
                        <i className="percent icon"/>
                        {this.props.el.discount}
                    </span>
                    <span className="left floated">
                        <i className="ruble sign icon"/>
                        <a>{this.props.el.price}</a>
                    </span>
                </div>
            </div>
        )
    }

}

export default Good