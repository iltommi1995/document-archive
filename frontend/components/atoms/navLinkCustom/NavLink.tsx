import Link from 'next/link'

export default function NavLinkCustom(props) {
    return (
        <Link href={props.href} passHref>
            {props.children}
        </Link>
    )
}
