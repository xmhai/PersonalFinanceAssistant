import React from 'react';
import { Link } from 'react-router-dom';
import { Container, Dropdown, Image, Menu } from 'semantic-ui-react'
  
class AppMenu extends React.Component {
    render() {
        return (
            <div id="menu">
                <Menu fixed='top' inverted>
                    <Container>
                        <Menu.Item as='a' header>
                            <Image size='mini' src='/logo.png' style={{ marginRight: '1.5em' }} />
                            <font color="#F2B233">PFA</font>
                        </Menu.Item>
                        <Menu.Item><Link to={`/`}>Home</Link></Menu.Item>
                        <Menu.Item><Link to={`/accounts`}>Account</Link></Menu.Item>

                        <Dropdown item text='Stock'>
                            <Dropdown.Menu>
                                <Dropdown.Item text='Portfolio' as={Link} to='/portfolios' />
                                <Dropdown.Item text='Transaction' as={Link} to='/transactions' />
                                <Dropdown.Item text='Dividend' as={Link} to='/dividends' />
                                <Dropdown.Item text='Profit' as={Link} to='/profits' />
                            </Dropdown.Menu>
                        </Dropdown>

                        <Dropdown item text='Config'>
                            <Dropdown.Menu>
                                <Dropdown.Item text='Stock' as={Link} to='/stocks' />
                            </Dropdown.Menu>
                        </Dropdown>
                    </Container>
                </Menu>
            </div>
        );
    }
}

export default AppMenu;